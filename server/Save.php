<<<<<<< HEAD

<?php
include_once 'db.php';
class Save{
    private $db;
    public function __construct(){
        $this->db = new DbConnect();
    }
    public function savePaymentDetail($payment_id, $payment_state){
        $query = "insert into paypal_verification(payment_id, state) values ('$payment_id', '$payment_state')";
        $inserted = mysqli_query($this->db->getDb(), $query);
        if($inserted == 1){
            $json = Array('success' => '1');
        }else{
            $json = Array('success' => '0');
        }
        mysqli_close($this->db->getDb());
        echo json_encode($json, JSON_PRETTY_PRINT);
    }
}
?>
>>>>>>> e611f43ef5b4a08ea7ae1092daa4f46c20b43c46
=======
<?php
include_once 'db.php';
class Save{
    private $db;
    public function __construct(){
        $this->db = new DbConnect();
    }
    public function savePaymentDetail($payment_id, $payment_state){
        $query = "insert into paypal_verification(payment_id, state) values ('$payment_id', '$payment_state')";
        $inserted = mysqli_query($this->db->getDb(), $query);
        if($inserted == 1){
            $json = Array('success' => '1');
        }else{
            $json = Array('success' => '0');
        }
        mysqli_close($this->db->getDb());
        echo json_encode($json, JSON_PRETTY_PRINT);
    }
}
?>
>>>>>>> e611f43ef5b4a08ea7ae1092daa4f46c20b43c46
