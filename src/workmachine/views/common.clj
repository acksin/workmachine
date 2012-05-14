(ns workmachine.views.common
  (:use [noir.core :only [defpartial]]
        [hiccup.page :only [include-css html5]]))

(defpartial layout [& content]
  (html5
   [:head
    [:title "workmachine"]
    (include-css "/bootstrap/css/bootstrap.css")]
   [:body
    [:div.container
     content]]))
