(ns api.project.write
  (:require [api.db.db-util :as db]
            [api.user.fetch :as fetch-user]
            [clojure.data.json :as json]
            [clojurewerkz.neocons.rest.cypher :as cy]))

(defn req-id
  [req]
  (Long/parseLong
    (-> req :route-params :id)))

(defn parse-payload
  [req]
  (json/read-str
    (slurp
      (:body req))))


;; Making a Project
;;--------------------------

; Get misc metadata as nodes
(defn make-root-project-node
  [payload]
  (cy/tquery db/conn
             "CREATE (proj:Project {title:{title}, description:{description}})
              RETURN ID(proj) as id"
              {:title (get payload "title")
               :description (get payload "description")}))


;; Connecting Participants
;;--------------------------

;; Adds participant relationship starting at UID to PID
(defn make-participant-rel
  [pid uid]
  (cy/tquery db/conn
             "MATCH (user),(proj)
              WHERE ID(user)={uid} AND ID(proj)={pid}
              CREATE (user)-[r:PARTICIPANT]->(proj)
              RETURN user"
             {:uid uid :pid pid}))

(defn make-all-participant-rels
  [proj-id user-id-list]
  (map (fn [uid] (make-participant-rel proj-id uid)) user-id-list))

;; Handler for relating a list of users to a project
(defn participant-rel-handler
  [req]
  (let [proj-id (req-id req)
        user-ids (parse-payload req)]
    (make-all-participant-rels proj-id user-ids)))



;; TODO
;; Wrap this in a transaction. The WHOLE DAMN THING
;;
;; Returns project ID
(defn handle-new-project
  [req]
  (let [parsed-body       (parse-payload req)
        root-project-node (first (make-root-project-node parsed-body))
        proj-id           (get root-project-node "id")]
    (make-all-participant-rels
      proj-id
      (get parsed-body "participants"))
    (str proj-id)))

