(ns workmachine.core
  (:use compojure.core)
  (:use clj-html.core)
  (:require [compojure.route :as route]
            [compojure.handler :as handler]
            [resque-clojure.core :as resque]
            [workmachine.workflow :as workflow]
            [workmachine.jobs :as jobs]
            [workmachine.work :as work]
            [workmachine.data-input :as input-types]
            [workmachine.data-output :as output-types]
            [workmachine.blueprints.content :as content-workflow]))

(defn run-workflow []
  (str (content-workflow/workflow [{"topic" "http://google.com"}
                                   {"topic" "http://yahoo.com"}
                                   {"topic" "http://amazon.com"}])))

(defroutes main-routes
  (GET "/" [] (str (jobs/number-of-available-jobs)))
  (GET "/assignments" [] (str @jobs/assigned-jobs))
  (GET "/available" [] (str @jobs/available-jobs))
  (GET "/workflow" [] (run-workflow))
  (GET "/assign/:worker-id" [worker-id] (work/assign worker-id))
  (POST "/submit/:worker-id" {params :params} (str params));(work/submit worker-id))
  (GET "/unassign/:worker-id" [worker-id] (work/unassign worker-id))
  (route/not-found "<h1>Page not found</h1>"))

;(resque/start [(resque-queues :work)])

(def app (handler/site main-routes))
