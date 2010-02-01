(ns fleet.runtime
  (:import
    [clojure.lang Sequential IObj]
    [fleet.util CljString]))

(defn- escaped-with
  [s]
  (or (-> s meta :escaped-with) {}))

(defn raw
  "Prevent encoding of string."
  [f s]
  (let [obj (-> s .toString CljString.)
        new-escw (assoc (escaped-with s) f true)
        new-meta (assoc (meta obj) :escaped-with new-escw)]
    (with-meta obj new-meta)))

(defn raw?
  [f s]
  ((escaped-with s) f))

(defmulti screen
  "Process and collect template string(s)."
  (fn [f s] (class s)))

(defmethod screen CharSequence
  [f s]
  (raw f (if (raw? f s) s (f s))))

(defmethod screen Sequential
  [f s]
  (raw f (apply str (map (partial screen f) s))))

(defmethod screen nil
  [f s]
  (raw f ""))

(defn make-runtime
  "Create runtime functions applied to specified escape-fn."
  [escape-fn]
  { :raw    (partial raw escape-fn)
    :raw?   (partial raw? escape-fn)
    :screen (partial screen escape-fn)
    })