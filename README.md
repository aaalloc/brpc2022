


<h1>Brpc2022</h1>

  <p>
    Event Stream Processing with BeepBeep3 and BeamNG.
    <br />
    <a href="https://github.com/github_username/repo_name">View Demo</a>
    ·
    <a href="https://github.com/github_username/repo_name/issues">Report Bug</a>
  </p>
</div>


<details>
  <summary>Table of Contents</summary>
  <ol>
    <li>
      <a href="#about-the-project">About The Project</a>
    </li>
    <li>
      <a href="#getting-started">Getting Started</a>
      <ul>
	    <li><a href="#note">Note</a></li>
        <li><a href="#prerequisites">Prerequisites</a></li>
        <li><a href="#beamngpy">BeamNGPy</a></li>
        <li><a href="#ide-configuration">IDE Configuration</a></li>
	         <ul>
	        <li><a href="#intellij">IntelliJ</a></li>
	        </ul>
	        <ul>
	        <li><a href="#python-module">Python module</a></li>
	        </ul>
      </ul>
    </li>
    <li><a href="#usage">Usage</a></li>
    <li><a href="#contact">Contact</a></li>
  </ol>
</details>



# About The Project
This project consist in a use case of BeepBeep with BeamNG.



# Getting Started

## Note
We highly suggest you to use [IntelliJ](https://www.jetbrains.com/idea/) with [Python plugin](https://plugins.jetbrains.com/plugin/631-python) if you want to work with what have been done.

## Prerequisites
* Windows 10 (BeamNG seems to not work on Linux)

* Last version of [Python](https://www.python.org/downloads/)

* BeamNGPy package, just do `pip install beamngpy` into your shell terminal

* BeamNG.tech license (_you can ask yours [here](https://register.beamng.tech/)_)

## BeamNGPy
 ##### Work in progress
To use BeamNGPy library, you need to setup your tech.key received with your BeamNG.tech license, we suggest you to check their [github](https://github.com/BeamNG/BeamNGpy#prereqs).

## IDE Configuration
### IntelliJ
You have to simply clone the repo, open the folder in Intelj and wait gradle build to finish and you are done, or almost.

#### Python module
Intelj have a wonderful Python plugin that make possible a better workflow (instead of using PyCharm, you now use only one IDE). To setup this follow the instruction below :

 1. Open Project Structure : File > Project structure ...
 2. Go to Project settings > Modules section
 3. Select "+" sign and choose "Import module"
 4. Select src/python folder
 5. Select "Create module from existing sources"
 6. Be sure to have checked the checkbox with the absolute path with the python folder that you selected
 7. Select "+" sign and Add new Python SDK
 8. Choose a location, we suggest you to choose `...\src\main\python\venv`, check _Inherit global site-packages_
 9. Django framework are detected, uncheck if you don't want to use it in the project (we don't use it so we suggest you to uncheck it), and click __Create__
 
 Now the python folder is considered has a Python module, but you have to select the SDK that you created just before for the python module, apply and everything is good to go. We suggest you to restart your IDE and now normaly everything is setup.

__GIF TO SHOW THE PROCESS__

# Usage
 ##### Work in progress
Use this space to show useful examples of how a project can be used. Additional screenshots, code examples and demos work well in this space. You may also link to more resources.



<!-- CONTACT -->
# Contact

_Alexander Yanovskyy_ : contact@yanovskyy.com
_Baptiste Wetterwald_ : baptiste.wetterwald@github.com


