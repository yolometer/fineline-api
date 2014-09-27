(ns api.project.write
  (:require [api.db.db-util :as db]
            [api.user.fetch :as fetch-user]
            [clojure.data.json :as json]
            [clojurewerkz.neocons.rest.nodes :as nn]
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
  (nn/create db/conn {:title (get payload "title")
                      :description (get payload "description")}))

;; TODO map user to project
(defn make-participant-rel
  [uid]
  uid)

;;  (let [uid-list (get payload "participants")]
(defn get-all-participant-nodes
  [uid-list]
  (map fetch-user/user-by-id uid-list))

;; TODO
;; Wrap this in a transaction. The WHOLE DAMN THING
(defn handle-new-project
  [req]
  (let [parsed-body (parse-payload req)
        root-node (make-root-project-node parsed-body)
        participant-nodes (get-all-participant-nodes
                            (get parsed-body "participants"))]
    (map :id participant-nodes)))


