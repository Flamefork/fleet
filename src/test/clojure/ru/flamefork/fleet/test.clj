(ns ru.flamefork.fleet
  (:use clojure.test)
  (:use clojure.contrib.pprint))

(deftest basics
  (pprint (fleet
"<p><(post :body)></p>
<ul>
  <(for [tag (post :tags] \">
    <li><(tag)></li>
  <\")>
</ul>")))
