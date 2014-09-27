(ns api.handler
  (:require [api.project.read :as proj-read]
            [api.project.write :as proj-write]
            [api.user.create :as new-user]
            [compojure.core :refer :all]
            [compojure.handler :as handler]
            [compojure.route :as route]))

(defroutes app-routes
  (GET "/" [] "Hello World")
  (POST "/project" [] proj-write/handle-new-project)
  (GET "/project/:id" [] proj-read/load-project-from-req)
  (POST "/project/:id" [] proj-write/handle-new-project)
  (POST "/user" [] new-user/handle-new-user)
  (route/not-found "Not Found"))

(def app
  (handler/site app-routes))
