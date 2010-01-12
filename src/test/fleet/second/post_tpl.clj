(do
  (use 'ru.flamefork.fleet.runtime)
  (fn [post]
    (screen [
      "<p>" (screen (post :body)) "</p>\n<ul>\n  "
      (screen (for [tag (post :tags)]
        (screen [
          "\n    <li>" (screen (str tag)) "</li>\n  "])))
      "\n</ul>"])))
