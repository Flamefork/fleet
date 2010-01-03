(ns ru.flamefork.fleet
  (:use
    [clojure.test]
    [clojure.contrib.pprint]
    [clojure.contrib.def]))

(deftest basics
  (pprint (fleet
"<p><(post :body)></p>
<ul>
  <(for [tag (post :tags)] \">
    <li><(str tag)></li>
  <\")>
</ul>")))

(deftest handmade-compilation
  (defvar f
    (partial apply str))

  (println
    ((fn [post]
      (f ["<p>" (f (post :body)) "</p>\n<ul>\n"
                  (f (for [tag (post :tags)]
                    (f ["<li>" (f (str tag)) "</li>\n"])))
                  "</ul>"])
      ) {:body "Body" :tags ["tag1" "tag2" "tag3"]})))