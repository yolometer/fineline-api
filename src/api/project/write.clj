(ns api.project.write
  (:require [api.db.db-util :as db]
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
(defn get-all-participants-nodes
  [uid-list]
  (lazy-seq
    (cons
      (make-participant-rel
        (first uid-list))
      (get-all-participants-nodes (next uid-list)))))

;; TODO
;; Wrap this in a transaction. The WHOLE DAMN THING
(defn handle-new-project
  [req]
  (let [parsed-body (parse-payload req)
        root-node (make-root-project-node parsed-body)]
    root-node))


