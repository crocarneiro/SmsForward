package br.com.smsforward.model.origin;

import androidx.room.Entity;
import androidx.room.Index;
import androidx.room.PrimaryKey;

import java.util.regex.Pattern;

@Entity(tableName = "origins",
        indices = {@Index(value = {"address"}, unique = true)})
public class Origin {
    @PrimaryKey
    private Long id;
    private String address;

    public Origin() {
    }

    public Origin(String address) {
        this.address = address;
        validateAddress();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    private void validateAddress() {
        if(address == null) throw new NullAddressException();
        if(address.isEmpty()) throw new EmptyAddressException();
        if(!isNumeric(address)) throw new NotNumericAddressException();
    }

    private boolean isNumeric(String strNum) {
        Pattern pattern = Pattern.compile("-?\\d+(\\.\\d+)?");
        if (strNum == null) {
            return false;
        }
        return pattern.matcher(strNum).matches();
    }
}
