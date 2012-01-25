(ns workengine.data-output)

(defn string [name]
  {:name name
   :html [:input {:type "text"
                  :value ""
                  :name name}]})

(defn text [name]
  {:name name
   :html [:textarea {:name name}]})

(defn text-list [name]
  {:name name
   :html [:textarea {:name name}]})
