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
             {:pid pid}))

; Parse shit from req
(defn load-project-from-req
  [req]
  (str {:_id (req-id req)}))

;; TODO return all users on a project
(defn get-all-users-handler
  [req]
  (let [pid (req-id req)]
    (println (get-project-participants pid))))
