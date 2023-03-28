# Kotlin SDK
This is a open source project on kotlin for NEAR protocol.

# Description

Near Android SDK implemented in Kotlin to interact with the NEAR blockchain.

# Features
* Includes Login into Near wallet
* Send transactions
* Retrieve information about accounts and contracts
* Sign and send transactions
* View access keys and more.

# Requirements

•	Minimum android version API 26+

•	Minimum Java 8 version

# RPC Calls
We support [THIS](https://docs.near.org/api/rpc/introduction) rpc calls.

## Configuration

First add dependency to your app build.gradle
```
implementation 'com.github.near:near-api-kotlin:1.0.14`
```

then, add the next resource in your settings.gradle

```
maven { url "https://jitpack.io" }
```

## Usage

In your manifest add the next permissions

```
<uses-permission android:name="android.permission.ACCESS_NETWORK_STATE" />
<uses-permission android:name="android.permission.INTERNET" />
```
you should have an activity and inside you have to add an intent filter

```
<activity
            android:name=".DemoActivity"
            android:exported="true">
            <intent-filter>
                <action android:name="android.intent.action.MAIN" />
                <category android:name="android.intent.category.LAUNCHER" />
            </intent-filter>
            <intent-filter>
                <action android:name="android.intent.action.VIEW" />
                <category android:name="android.intent.category.DEFAULT" />
                <category android:name="android.intent.category.BROWSABLE" />
                <data
                    android:host="success-auth"
                    android:scheme="near" />
            </intent-filter>
</activity>
```

this will help your activity catch the data we need after login into your Near wallet account in your browser.

# First
## Login into your account

The first thing you should do before accessing all the other functions in the Near SDK library is logging into yout Near wallet account.

In your activity you should create an instance of ``` NearMainService ```

```
private var nearMainService: NearMainService = NearMainService(this)
```

then, call login() method. This will call an Intent to open browser and login into your Near wallet account.

```
nearMainService.login("yourusername.testnet")
```

Finally, override the onResume() method and check if login was successfull.

```
    override fun onResume() {
        super.onResume()
        val uri = intent.data
        if (nearMainService.attemptLogin(uri)){
            // Do anything after logging in
        }
    }
```
Once you are logged in you can use any method inside ``` NearMainService ``` class.

## Make a Transaction

After user is logged in, call ``` sendTransaction() ``` method with the receiver user name and Near amount inside a ``` Coroutine ``` to wait for the response

```
CoroutineScope(Dispatchers.IO).launch {
    val sendMoney = withContext(Dispatchers.IO) {
        nearMainService.sendTransaction("receiverusername.testnet", "1")
      }
}
```

and thats it, that's how you make a transaction.

In this repository we have an example of how to make a transaction and also how to call a [contract method as view function][1].


  [1]: https://google.com

## Demo and Screenshots

![Screenshot_login](https://user-images.githubusercontent.com/36077886/183770807-9ca17193-ec95-454c-be1f-a45b4d76c8bd.png)

![Screenshot_transaction](https://user-images.githubusercontent.com/36077886/183782655-1aed08a2-8061-4e68-a671-bdc760bd15ee.png)



After building the demo project in you local, you will be able to see the first screen where you have to type your user name and continue to login in the browser.
 By clicking on the "Transaction" button 1 Near is send to "android-test-23.testnet" default account ([Transaction docs][2]) and finally prints the response in the logs and displays it on the screen.


  [2]: https://docs.near.org/api/rpc/transactions#send-transaction-async
 
 If you click "Call function" button, contract methods are going to be called as [view functions][3].


  [3]: https://docs.near.org/api/rpc/contracts#call-a-contract-function

In this case we have a call for 3 different contracts that receives some parameters and returns a call back and finally it prints the response in the logs and displays it on the screen.

```
    private fun callFunction() {
        var balanceOfArgs = "{ \"tokenOwner\": \"android-test-22.testnet\" }"
        val contractName = "android-test-22.testnet"
        val balanceOfResponse = this.nearMainService.callViewFunction(contractName, "balanceOf", balanceOfArgs)
        if(balanceOfResponse.error == null) {
            val functionResult = balanceOfResponse.result.result!!.getDecodedAsciiValue()
        }

        val totalSupplyArgs = "{}"
        val totalSupplyResponse = this.nearMainService.callViewFunction(contractName, "totalSupply", totalSupplyArgs)
        if(totalSupplyResponse.error == null) {
            val functionResult = totalSupplyResponse.result.result!!.getDecodedAsciiValue()
        }

        val transferFromArgs = "{ \"from\": \"android-test-22.testnet\", \"to\": \"android-test-23.testnet\", \"tokens\": \"1\" }"
        val transferFromResponse = this.nearMainService.callViewFunction(contractName, "transferFrom", transferFromArgs)
        if(transferFromResponse.error == null) {
            val functionResult = totalSupplyResponse.result.result!!.getDecodedAsciiValue()
        }

        balanceOfArgs = "{ \"tokenOwner\": \"android-test-22.testnet\" }"
        val callViewFunctionTransactionResponse = this.nearMainService.callViewFunctionTransaction(contractName, "balanceOf", balanceOfArgs)
        if(callViewFunctionTransactionResponse.result.status.Failure == null) {
            callViewFunctionTransactionResponse.result.status.SuccessValue?.let {
                val decodedValue = String(Base64.getDecoder().decode(it))
            }
        }

        val invalidBalanceOfArgs = "{ tokenOwner: android-test-22.testnet }"
        val errorResponse = this.nearMainService.callViewFunctionTransaction(contractName, "balanceOf", invalidBalanceOfArgs)
    }
```
In this block of code we make the calls by using ``` nearMainService.callViewFunction() ``` and ``` nearMainService.callViewFunctionTransaction() ```
