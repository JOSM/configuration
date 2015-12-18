# JOSM-configuration

A JOSM plugin to make your mapping life easier.

####  Installing 
To install this plugin the user can download the jar [from here](https://github.com/mapbox/JOSM-configuration/releases/tag/v1.1) and copying it to JOSM’s plugin folder. On OSX this is `~/Library/JOSM/plugins`. Activate the plugin from `JOSM Preferences > Plugins` and restart JOSM.

#### Usage
![screenshot 2015-12-11 15 00 22](https://cloud.githubusercontent.com/assets/126868/11740351/efb8d556-a017-11e5-90a4-fec9a0438b17.png)
- The ‘Task Config’ menu lists common tasks that you can choose from. This startup list is universal for all users of the plugin and can be modified [here](/config-list.json).
- The configuration for each task includes imagery layers, mappaint styles, filters, changeset comment and source specific for the mapping task.
- Use `clear` to remove any configureation settings that were applied and restore your environment.

#### Custom configuration
- You can make your own task configuration using such a JSON structure
```javascript{
  "project": {
    "type": "task",
    "name": " ",
    "description": " ",
    "doc_url": " ",
    "changeset": {
      "comment": " ",
      "source": " "
    },
    "imagery": [ 
      {"name": " ",
        "url": " "
      }],
    "filters": [{
      "filter": " "
    }],
    "mapcss": [{
      "name": " ",
      "description": " ",
      "url": " "
     }],
    "contact_name": "",
    "contact_email": ""
  },
  "tags": [{
    "key": " ",
    "value": " "
  }]
}
```
- Host your JSON online, the simplest is to use a GitHub Gist
- From the plugin menu use `Load task from URL` to load your settings. The task config is stored only during the user session and is lost on reloading JOSM.

#### Working
The plugin currently works by passing clicking the `task config` button which provides a list of tasks to choose from.

![task_config](https://cloud.githubusercontent.com/assets/4470913/11678750/a4873e4a-9e70-11e5-92f5-3f721d134011.gif)

Any issues you find can be registered [here.](https://github.com/aarthykc/task-components-JOSM-plugin/issues)
