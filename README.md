# JOSM-configuration

####  Installing 
To install this plugin the user can download the jar [from here](https://github.com/mapbox/JOSM-configuration/releases/tag/v1.1) and copying it to JOSM’s plugin folder.The user can begin by clicking on the ‘task config’ menu item which will provide a list of tasks that the can chose from. The user can choose the task he/she wishes to work on and it will load the configurations automatically. The configurations include setting the layers, mappaint styles, filters, changeset comment and source. Addtionally, it allows the user to switch between tasks without any duplication. It also allows one to make their own task and load the task configuration file which is saved locally. Once the user is done with the task he/she can click `clear` which will clear all task specific configurations.

To add a task locally click on `Load task from URL` this saves your changes locally and terminates once you restart JOSM. The JSON structure is as given below: 

javascript{
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

#### Working
The plugin currently works by passing clicking the `task config` button which provides a list of tasks to choose from.

![task_config](https://cloud.githubusercontent.com/assets/4470913/11678750/a4873e4a-9e70-11e5-92f5-3f721d134011.gif)

Any issues you find can be registered [here.](https://github.com/aarthykc/task-components-JOSM-plugin/issues)
