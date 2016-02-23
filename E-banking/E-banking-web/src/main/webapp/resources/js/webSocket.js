var webSocket = new WebSocket("ws://10.16.164.138:8083/Telefonia/serversocket");
var messagesTextArea = document.getElementById("menssagesTextArea");

webSocket.onopen = function (message) {
    processOpen(message);
};
webSocket.onclose = function (message) {
    processClose(message);
};
webSocket.onerror = function (message) {
    processError(message);
};
webSocket.onmessage = function (message) {
    processMessage(message);
};
function processOpen(message) {
    messagesTextArea.value += "Server Connect..." + "\n";

}
function processError(message) {
    messagesTextArea.value += "error..." + "\n";

}
function processClose(message) {
    messagesTextArea.value += "Server Desconectado..." + "\n";

}
function processMessage(message) {
    alert("El número telefónico no existe " + message.data);
    var alerta = message.data;
    valorq.value = alerta;

//                messagesTextArea.value += "Recibido del servidor..." + message.data +"\n";                
//                if(message.data == "NO"){
//                    document.getElementById("valorq").value=message.data.toString();
//                    alert("El número telefónico no existe");
//                }

}
function sendMessage() {
    alert("El número telefónico " + textTelefono.value);
    if (textTelefono.value != "close") {
        var mensaje = textTelefono.value + "|" + textValor.value;
        webSocket.send(mensaje);
        textTelefono.value = " ";
        textValor.value = " ";
        alert(mensaje);
        // messagesTextArea.value = "Enviado al servidor..." + mensaje + "\n";


    } else
        webSocket.close();
}
function sendPrimaryMessage() {
    
    if (textTelefono.value != "close") {
        var mensaje = textTelefono.value;
        webSocket.send(mensaje);
        textTelefono.value = " ";
        textValor.value = " ";
        alert(mensaje);
        // messagesTextArea.value = "Enviado al servidor..." + mensaje + "\n";


    } else
        webSocket.close();
}