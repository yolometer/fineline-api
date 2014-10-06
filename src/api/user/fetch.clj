(ns api.user.fetch
  (:require [api.db.db-util :as db]
            [clojure.data.json :as json]
            [clojurewerkz.neocons.rest.nodes :as nn]))

(defn req-id [req] (-> req :route-params :id))

;; TODO make cypher query?
;; search for USERs
(defn user-by-id
  [uid]
  (nn/get db/conn uid))

(defn user-by-id-handler
  [req]
  (let [user-node (user-by-id (Long/parseLong (req-id req)))]
    (json/write-str (:data user-node))))
