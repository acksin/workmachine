(ns workmachine.server
  (:require [noir.server :as server]
            [cheshire.core :as json]))

;; For JSON body.
(defn backbone [handler]
  (fn [req]
    (let [neue (if (= "application/json" (get-in req [:headers "content-type"]))
                 (update-in req [:params] assoc :backbone (json/parse-string (slurp (:body req)) true))
                 req)]
      (handler neue))))

(server/add-middleware backbone)

(server/load-views "src/workmachine/views/")

(defn -main [& m]
  (let [mode (keyword (or (first m) :dev))
        port (Integer. (get (System/getenv) "PORT" "8080"))]
    (server/start port {:mode mode
                        :ns 'workmachine})))

