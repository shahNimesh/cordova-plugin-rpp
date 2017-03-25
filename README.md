# Cordova Plugin Thermal Portable Receipt Printer

## Description

Plugin for thermal portable printer, In this moment I only tested in model:

- RPP200 48mm
- RPP300 72mm

I think the driver can work for ore models but I can't confirm.

***Important at this moment only works for ANDROID***

## Installing the plugin

```
cordova plugin add https://github.com/CXRom/cordova-plugin-rpp.git
```

## Quick Example

```javascript

Rpp.Connect("00:0E:0E:0B:7B:93", // <-- MAC Address of the printer
  function(print) {
    //At this point we send the action but we need to wait until the connection
    console.log(`connect ok ${JSON.stringify(print)}`);
  },
  function (err){
    console.log(`connect err ${JSON.stringify(err)}`);
  });

//Ask is device is connected
Rpp.IsConnected(function(conn) {
  //Send to print
  Rpp.Print({
    marginTop: 10, //Margin before print
    marginBottom: 10, //Margin after print
    lineSpacing: 50, //Size of line
    lines: [ //Lines to print
      { text: "Title", align: 1, bold: true, underline: true, size: 17 }, //long name properties
      { text: "Subtitle", a: 1, b: true, u: true, s: 17 }, //short name properties
      { text: "normal line" },
      { text: ":)", h: true }
    ]
  }, function(res) {
    console.log(`print ok ${JSON.stringify(res)}`);
  }, function(err){
    console.log(`print err ${JSON.stringify(err)}`);
  });
}, function(err) {

});
```

## API

### Connect(macAddress, success, fail)

Initialize the connection with the printer. Its important that the printer has been paired before.

### IsConnected(success, fail)

Ask is the printer is already connected, its important to confirm the connection before we try to print.

### Close(success, fail)

Force the close of connection.

### Print(data, success, fail)

Send lines to print in JSON

```javascript
Rpp.Print({
  marginTop: 10, //Margin before print
  marginBottom: 10, //Margin after print
  lineSpacing: 50, //Size of line
  lines: [ //Lines to print, each item represent one line
    { text: "Title", align: 1, bold: true, underline: true, size: 17 }, //long name properties
    { text: "Subtitle", a: 1, b: true, u: true, s: 17 }, //short name properties
    { text: "normal line" },
    { text: ":)", h: true }
  ]
}, function(res) {
  console.log(`print ok ${JSON.stringify(res)}`);
}, function(err){
  console.log(`print err ${JSON.stringify(err)}`);
});
```

Each line can be configured with this properties:

| short | long      | type   | description                         |
| ----- | --------- | ------ | ----------------------------------- |
| a    | align      | int    | 0=Left (default), 1=Center, 2=Right |
| b    | bold       | bool   | false (default), true               |
| u    | underline  | bool   | false (default), true               |
| u    | highlight  | bool   | false (default), true               |
| s    | font size  | int    | 0-17                                |

### PrintAndClose(data, success, fail)

The same as print but after print close the connection.
