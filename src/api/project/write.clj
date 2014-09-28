(ns api.project.write
  (:require [api.db.db-util :as db]
            [api.user.fetch :as fetch-user]
            [clojure.data.json :as json]
            [clojurewerkz.neocons.rest.nodes :as nn]
            [clojurewerkz.neocons.rest.cypher :as cy]
            [clojurewerkz.neocons.rest.relationships :as nrl]))

(defn req-id [req] (-> req :route-params :id))

(defn parse-payload
  [req]
  (json/read-str
    (slurp
      (:body req))))

; Get misc metadata as nodes
(defn make-root-project-node
  [payload]
  (cy/tquery db/conn
             "CREATE (proj:Project {title:{title}, description:{description}})
              RETURN ID(proj) as id"
              {:title (get payload "title")
               :description (get payload "description")}))

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

(defn get-all-participant-nodes
  [uid-list]
  (map fetch-user/user-by-id uid-list))

;; TODO
;; Wrap this in a transaction. The WHOLE DAMN THING
(defn handle-new-project
  [req]
  (let [parsed-body (parse-payload req)
        root-project-node (first (make-root-project-node parsed-body))]
    (make-all-participant-rels
      (get root-project-node "id")
      (get parsed-body "participants"))
    (str (get root-project-node "id"))))

