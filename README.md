# sample-currency-list

Implement a dataset to display a list in Recyclerview and show how to handle data between Activity and Fragment.

* Module structure:
<br>![image](https://github.com/KevinJ1008/sample-currency-list/blob/main/CurrencySample.drawio.png)<br>
  * BaseCore: Handle some util, base, or common logic
  * LocalClient: DB logic module
  * Widget: Handle some custom views
  * app: Business logic, inclding feature UI and MVVM structure which by this feature
  * NOTICE: If we have to extend project business, we may develop some businees or feature module to make sure not to contain too much code in it

* Apply library:
  * DI: Koin
  * Thread control: Coroutine
  * RecyclerView: Epoxy
  * Jetpack: LiveData, ViewModel, Room
