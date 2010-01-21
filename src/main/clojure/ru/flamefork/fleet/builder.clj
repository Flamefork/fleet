(ns ru.flamefork.fleet.builder
  (:use
    [clojure.contrib.def]
    [ru.flamefork.fleet.runtime])
  (:require
    [clojure.contrib.str-utils2 :as su]))

(defn- consume)

(defn- escape-string
  [s]
  (.replace (.replace s "\\" "\\\\") "\"" "\\\""))

(defvar- consumers {
  :text  #(str "(raw \"" (escape-string %) "\")")
  :clj   #(str % "\n")
  :embed #(raw (apply str ["(screen escape-fn (" (raw (apply str (map consume %))) "))"]))
  :tpl   #(raw (apply str ["(screen escape-fn [" (raw (apply str (map consume %))) "])"]))
  })

(defn- consume
  [ast]
  (let [[type content] ast
        consumer (type consumers)]
    (consumer content)))

(defn wrap-template
  [args s]
  (read-string (str "
  (do
    (use 'ru.flamefork.fleet)
    (use 'ru.flamefork.fleet.runtime)
    (let [escape-fn @ru.flamefork.fleet/escape-fn]
    (fn [" (su/join " " args) "]" s ")))")))

(defn build
  "Build Clojure forms from template-str."
  [args ast]
  (wrap-template args (consume ast)))
