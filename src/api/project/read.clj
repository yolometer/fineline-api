(ns api.project.read
  (:require [api.db.db-util :as db]
            [clojure.data.json :as json]
            [clojurewerkz.neocons.rest.cypher :as cy]))

(defn req-id [req] (-> req :route-params :id))

(defn get-project-participants
  [pid]
  (cy/tquery db/conn
              "MATCH (proj)
              WHERE ID(proj)={pid}
              RETURN proj"
             {:pid 27}))

; Parse shit from req
(defn load-project-from-req
  [req]
  (str {:_id (req-id req)}))

; Get all project data that has been updated since a specific time
(defn get-project-since
  [timestamp]
  timestamp)

(defn get-all-users-handler
  [req]
  (let [pid (req-id req)]
    (println (get-project-participants pid))))
