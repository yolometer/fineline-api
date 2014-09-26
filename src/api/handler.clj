(ns api.handler
  (:require [api.project.project :as project]
            [compojure.core :refer :all]
            [compojure.handler :as handler]
            [compojure.route :as route]))

(defroutes app-routes
  (GET "/" [] "Hello World")
  (GET "/project/:id" [] project/load-project-from-req)
  (route/not-found "Not Found"))

(def app
  (handler/site app-routes))
