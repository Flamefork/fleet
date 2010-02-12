(fn [runtime post] (let [{:keys [raw raw? screen]} runtime] (screen [(raw "<p>")(screen (raw (post :body)))(raw "</p>
<ul>
  ")(screen (for [tag (post :tags)] (screen [(raw "
    <li>")(screen (str tag))(raw "</li>
  ")])))(raw "
</ul>")])))