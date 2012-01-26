(ns workmachine.data-output)

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

(defn parse [field]
  (apply (case (first field)
           :string string
           :text text
           :text-list text-list)
         (rest field)))