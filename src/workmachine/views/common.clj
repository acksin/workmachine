(ns workmachine.views.common
  (:use [noir.core :only [defpartial]]
        [hiccup.page-helpers :only [include-css html5]])
  (:require [workmachine.engine.workflow :as workflow]
            [workmachine.engine.data-input :as data-input]
            [workmachine.engine.data-output :as data-output]))

(defpartial layout [& content]
  (html5
   [:head
    [:title "workmachine"]
    (include-css "/css/reset.css")]
   [:body
    [:div#wrapper
     content]]))
