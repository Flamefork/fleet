(ns fleet.builder
  (:use
    [clojure.contrib.def]
    [fleet runtime util])
  (:require
    [clojure.contrib.str-utils2 :as su]))

(defn- consume)

(defvar- consumers {
  :text  #(str "(raw \"" (escape-clj-string %) "\")")
  :clj   bypass
  :embed #(apply str ["(screen (" (apply str (map consume %)) "))"])
  :tpl   #(apply str ["(screen [" (apply str (map consume %)) "])"])
  })

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
    "(let [{:keys [raw raw? screen]} runtime] "
    (consume ast) "))"))