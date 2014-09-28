(ns api.project.read
  (:require [api.db.db-util :as db]
            [clojure.data.json :as json]
            [clojurewerkz.neocons.rest.cypher :as cy]))

(defn req-id [req]
  (Long/parseLong
    (-> req :route-params :id)))

(defn get-project-participants
  [pid]
  (map (fn [x] (assoc (:data (get x "user"))
                      "id" (get x "uid")))
      (cy/tquery db/conn
                 "MATCH (user)-[PARTICIPANT]->(proj)
                  WHERE ID(proj)={pid}
                  RETURN user, ID(user) as uid"
                 {:pid pid})))

; Parse shit from req
(defn load-project-from-req
  [req]
  (str {:_id (req-id req)}))

;; TODO return all users on a project
(defn get-all-users-handler
  [req]
  (let [pid (req-id req)]
    (json/write-str 
      (get-project-participants pid))))
