# task-components-JOSM-plugin

####  Installing 
install this plugin by downloading the jar [from here](https://github.com/aarthykc/task-components-JOSM-plugin/releases) and copying it to your JOSM’s plugin folder. You’ll see the ‘task components’ menu item click on it and enter the gist api url. The gist should contain a single JSON adhering to [this format](https://gist.github.com/aarthykc/c02c7c8817084a826110).
```
{"task": 
  { "layers": [{"url": 
               "name":  }],
      "filters": " ",
      "comment": " ",
      "source": " ",
      "mappaints": [{"name":,
                    "description":,
                    "url": }]
  }
}

```
_All fields are to be filled_
#### Working
The plugin currently works by passing a Github gist URL to a specific task configuration file. The gist API is then used to load the JSON from the URL and customize JOSM.

![josm task](https://cloud.githubusercontent.com/assets/10141319/11099079/49e01086-88cf-11e5-88cd-c4a3b8a6db4b.gif)

Any issues you find can be registered [here.](https://github.com/aarthykc/task-components-JOSM-plugin/issues)
