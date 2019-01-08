(ns fleet.runtime
  (:import
    [clojure.lang Sequential]
    fleet.util.CljString))

(defn- escaped-with
  [s]
  (:escaped-with (meta s) {}))

(defn raw
  "Prevent escaping of string by escaping-fn."
  [escaping-fn ^Object s]
  (let [obj (-> s .toString CljString.)
        new-escw (assoc (escaped-with s) escaping-fn true)
        new-meta (assoc (meta obj) :escaped-with new-escw)]
    (with-meta obj new-meta)))

(defn raw?
  "Check if string is already escaped by escaping-fn."
  [escaping-fn s]
  (get (escaped-with s) escaping-fn))

(defprotocol Screenable
  (screen [s escaping-fn] "Process and collect template string(s)."))

(extend-protocol Screenable
  CharSequence
    (screen [s f] (raw f (if (raw? f s) s (f s))))
  Sequential
    (screen [s f] (raw f (apply str (map #(screen %1 f) s))))
  Object
    (screen [s f] (raw f (str s)))
  nil
  (screen [_ _] nil))

(def ^clojure.lang.Keyword ^:dynamic *transDomain* "Translation (i18n) domain: Clojure keyword" nil)
(def ^clojure.lang.IFn     ^:dynamic translate
  (fn [escaping-fn in transDomain & args]
    (assert false (str "{fleet.runtime/translate unbound, input: '" in "'}"))))

(defn _
  "Translation function shorthand: will call (translate escaping-fn word transDomain)."
  ([escaping-fn word]
   (translate escaping-fn word *transDomain*))
  ([escaping-fn word transDomain]
   (translate escaping-fn word transDomain))
  ([escaping-fn word transDomain & args]
   (apply translate escaping-fn word transDomain args)))

(defn make-runtime
  "Create runtime functions applied to specified escaping-fn."
  [escaping-fn]
  {:raw    (partial raw  escaping-fn)
   :raw?   (partial raw? escaping-fn)
   :screen #(screen %1   escaping-fn)
   :_      (partial _    escaping-fn)})
