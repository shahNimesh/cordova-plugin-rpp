var Rpp = {
  Connect: function (address, successCallback, errorCallback) {
    cordova.exec(
      successCallback, // success callback function
      errorCallback, // error callback function
      'Rpp', // mapped to our native Java class called
      'connect', // with this action name , in this case 'beep'
      [address] )// arguments, if needed
  },

  IsConnected: function (successCallback, errorCallback) {
    cordova.exec(
      successCallback, // success callback function
      errorCallback, // error callback function
      'Rpp', // mapped to our native Java class called
      'isConnected', // with this action name , in this case 'beep'
      [] )// arguments, if needed
  },

  Close: function (successCallback, errorCallback) {
    cordova.exec(
      successCallback, // success callback function
      errorCallback, // error callback function
      'Rpp', // mapped to our native Java class called
      'close', // with this action name , in this case 'beep'
      [] )// arguments, if needed
  },

  Print: function (data, successCallback, errorCallback) {
    cordova.exec(
      successCallback, // success callback function
      errorCallback, // error callback function
      'Rpp', // mapped to our native Java class called
      'print', // with this action name , in this case 'beep'
      [data] )// arguments, if needed
  },

  PrintAndClose: function (data, successCallback, errorCallback) {
    cordova.exec(
      successCallback, // success callback function
      errorCallback, // error callback function
      'Rpp', // mapped to our native Java class called
      'printAndClose', // with this action name , in this case 'beep'
      [data] )// arguments, if needed
  }
}
module.exports = Rpp;
