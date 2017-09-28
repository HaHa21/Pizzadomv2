<?php

include_once 'Save.php';
 if($_SERVER['REQUEST_METHOD'] == 'POST' && isset($_POST)){
 $payment_id = $_POST['PAYMENT_ID'];
 $payment_state = $_POST['PAYMENT_STATE'];
 $saveObject = new Save();
 $saveObject->savePaymentDetail($payment_id, $payment_state);
 }
 
 ?>

