(ns ru.flamefork.fleet
  (:use
    [clojure.test]
    [clojure.contrib.pprint]
    [clojure.contrib.def]))


(deftest parse-test
  (println (parse
"<p><(post :body)></p>
<ul>
  <(for [tag (post :tags)] \">
    <li><(str tag)></li>
  <\")>
</ul>")))

(deftest compilation
  (println ((fleet '(post title)
"<p><(str title)></p>
<p><(post :body)></p>
<ul>
  <(for [tag (post :tags)] \">
    <li><(str tag)></li>
  <\")>
</ul>") {:body "Body" :tags ["tag1" "tag2" "tag3"]} "Post Template")))
