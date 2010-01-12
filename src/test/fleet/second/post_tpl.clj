(do
  (use 'ru.flamefork.fleet.runtime)
  (fn [post]
    (screen [
      (raw "<p>") (screen (raw (post :body))) (raw "</p>\n<ul>\n  ")
      (screen (for [tag (post :tags)]
        (screen [
          (raw "\n    <li>") (screen (str tag)) (raw "</li>\n  ")])))
      (raw "\n</ul>")])))
