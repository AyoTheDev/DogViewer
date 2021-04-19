# DogViewer

*To run this project open android studio and navigate to File > New > Project from version control and then paste the https link provided by git*

This Android app project was made using Kotlin, it uses the Dog API found here: https://thedogapi.com/.

For dependency injection Dagger is used. On the UI side, we're using MVVM and the rest of the project uses clean architecture where it is split into the following
layers/modules:

* **App**, this is where we implement everything concerned with the UI using MVVM
* **Api**, this contains all the code for talking to the back-end/cloud services
* **Domain**, this layer contains the business logic and data model entities. The point of this layer is to have a standard set of objects that can be reused within
different projects

* Unit tests have been written for the ViewModel and the usecases.

* The project adheres to the SOLID OOP principles in the following way:
  
  * **Single Responsibility**
    * The `DogsUseCase` is a good example of single responsibility as its one job is to retrieve data about the dogs. If we were to change 
    this class a valid reason might be that we want to return a filtered/queried list of characters (or campuses).

  * **Open Closed**
      * An example of this principle in the sample project is the `CoroutineContextProvider` which we use to provide thread contexts either to run on the UI or 
      in a background worker thread. We still need to specify thread contexts in our tests but we don't want to use those that are implemented by the original class
      so we extend a special case for tests called `TestContextProvider`
      
  * **Liskov Substitution**
      * Although not specifically created by myself and instead by the android framework, an example of this exists in our `MainViewModel` with its use of `LiveData`
      and `MutableLiveData`. We need to be able to expose LiveData to the `MainActivity` (the main view) and we also need to be able to modify or mutate this LiveData.
      So we have a private MutableLiveData `_dogsLiveData` for us to manipulate and then we expose it to the view with a public LiveData `dogsLiveData` this
      way we can modify our data source without carelessly exposing it to the View.
      
  * **Interface Segregation**
      * Similar to the previous point, this project didn't give much scope to implement principle myself but the Retrofit library that is used to make
      network calls in the API module has us create the interface `EndPoints` and then the library generates the implementation.
  
  * **Dependency Inversion**
      * Following from the previous point, our abstraction `EndPoints` is dependent on the `ApiService`. We do not concretely implement it within the class itself.
      
- - - -

*Further points for consideration*

* When querying the API the imageURL would return null so we had to have a base URL for the image and use the image id to be able to generate the image

* It would have been good to have a state for when the query shows no results

* A single activity approach would have been more suitable as fragments are more light weight and make transitions smoother