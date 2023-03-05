package perkmanager;

public enum Membership {
    CAA("CAA"),
    CREDITCARD("credit card"),
    AIRMILE("air mile");

    private String membershipName;

    Membership(String membershipName) {
        this.membershipName = membershipName;
    }

    public String getMembershipName() {
        return membershipName;
    }
}
