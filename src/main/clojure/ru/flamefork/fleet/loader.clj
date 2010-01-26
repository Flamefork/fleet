(ns ru.flamefork.fleet.loader
  (:import java.io.File)
  (:require
    [clojure.contrib.str-utils2 :as su]))

(defn- ns-from-path
  [path]
  (symbol (.. path
    (replace \_ \-)
    (replace \. \-)
    (replace File/separatorChar \.))))

(defn- names-from-filename
  "Make (post-html-fleet post-html post) from 'post.html.fleet'"
  [filename]
  (map #(ns-from-path (su/join \. %))
    (loop [s (su/split filename #"\.")
           names []]
      (if (nil? s)
        names
        (recur (butlast s) (conj names s))))))

(defn- relative-path
  "File path relative to root. Only if file inside root."
  [root file]
  (let [file-path (.getCanonicalPath file)
        root-path (.getCanonicalPath root)]
    (when (.startsWith file-path root-path)
      (.substring file-path (+ (.length root-path) 1)))))

(defn- make-tpl-info
  [root file]
  (let [ns (ns-from-path (relative-path root (.getParentFile file)))]
    {:source (slurp (.getPath file))
     :names (names-from-filename (.getName file))
     :ns ns}))

(defn make-tpl-infos
  [root-path file-filter]
  (let [root (File. root-path)
        nodes (file-seq root)
        files (filter file-filter nodes)]
    (map (partial make-tpl-info root) files)))