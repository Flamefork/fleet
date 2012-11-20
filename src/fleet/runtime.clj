(ns fleet.runtime
  (:import
    [clojure.lang Sequential IObj]
    fleet.util.CljString))

(defn- escaped-with
  [s]
  (:escaped-with (meta s) {}))

(defn raw
  "Prevent encoding of string."
  [escaping-fn s]
  (let [obj (-> s .toString CljString.)
        new-escw (assoc (escaped-with s) escaping-fn true)
        new-meta (assoc (meta obj) :escaped-with new-escw)]
    (with-meta obj new-meta)))

(defn raw?
  [escaping-fn s]
  (get (escaped-with s) escaping-fn))

(defprotocol Screenable
  (screen [s escaping-fn] "Process and collect template string(s)."))

(extend CharSequence Screenable
  {:screen (fn [s f] (raw f (if (raw? f s) s (f s))))})

(extend Sequential Screenable
  {:screen (fn [s f] (raw f (apply str (map #(screen %1 f) s))))})

(extend Object Screenable
  {:screen (fn [s f] (raw f (str s)))})

(defn make-runtime
  "Create runtime functions applied to specified escaping-fn."
  [escaping-fn]
  { :raw    (partial raw  escaping-fn)
    :raw?   (partial raw? escaping-fn)
    :screen #(screen %1 escaping-fn)
    })