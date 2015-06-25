(ns fleet.builder
  (:refer-clojure :exclude [replace])
  (:require
    [clojure.string :as su])
  (:use
    [fleet runtime util]))

(declare consume)

(def ^:private consumers {
  :text  #(str "(raw \"" (escape-clj-string %) "\")")
  :embed #(str "(screen (" (apply str (map consume %)) "))")
  :tpl   #(str "(screen [" (apply str (map consume %)) "])")
  :clj   bypass})

(defn- consume
  [ast]
  (let [[type content] ast
        consumer (type consumers)]
    (consumer content)))

(defn build
  "Build Clojure forms from template-str."
  [args ast]
  (str
    "(fn [runtime " (su/join " " args) "] "
    "(let [{:keys [raw raw? screen _]} runtime] "
    (consume ast) "))"))