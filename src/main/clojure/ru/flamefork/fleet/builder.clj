(ns ru.flamefork.fleet.builder
  (:use
    [clojure.contrib.def]
    [ru.flamefork.fleet.runtime])
  (:require
    [clojure.contrib.str-utils2 :as s]))

(defn- consume)

(defvar- consumers {
  :text  (fn [content escape-fn] `((raw ~content)))
  :clj   (fn [content escape-fn] (read-string (str "(" content "\n)")))
  :embed (fn [content escape-fn] `((screen ~escape-fn (~@(apply concat (map #(consume % escape-fn) content))))))
  :tpl   (fn [content escape-fn] `((screen ~escape-fn [~@(apply concat (map #(consume % escape-fn) content))])))
  })

(defn- consume
  [ast escape-fn]
  (let [[type content] ast
        consumer (type consumers)]
    (consumer content escape-fn)))

(defn build
  "Build Clojure forms from template-str."
  [args ast escape-fn]
  `(do
    (use 'ru.flamefork.fleet.runtime)
    (fn
      [~@args]
      ~@(consume ast escape-fn))))
