import io.cucumber.java.en.*;
import java.util.List;
import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class StepDefinitions {

    private Order o;
    private Catalogue catalogue = mock(Catalogue.class);


    @Given("the price of a {string} being {double} dollars")
    public void the_price_of_a_being_$(String drink, Double price) {
        when(catalogue.getPrice(drink)).thenReturn(price);
    }

    @Given("{word} who wants to create an Order")
    public void romeo_who_wants_to_create_an_order(String who) {
        o = new Order();
        o.setOwner(who);
    }
    @When("{word} is declared as recipient")
    public void juliette_is_declared_as_recipient(String who) {
        o.setRecipient(who);
    }

    @When("a(nother?) {string} is added to the order")
    public void add_drink_to_the_order(String drinkName){
        o.getDrinks().add(new Order.Drink(drinkName));
    }


    @Then("the order contains {int} drink(s?)")
    public void check_order_size(int size) {
        assertEquals(size, o.getDrinks().size());
    }

    @Then("the order does not contain any drinks")
    public void the_order_does_not_contain_any_drinks() {
        List<Order.Drink> drinks = o.getDrinks();
        assertEquals(0, drinks.size());
    }

    @Then("the order contains {int} {string}")
    public void check_order_contents(int size, String drink) {
        long count = o.getDrinks().stream()
                    .filter(d -> d.getName().equals(drink))
                    .count();
        assertEquals(size,count);
    }


    @Given("taxes in {word} being {double}%")
    public void taxes_being(String place, double rate) {
        o.setTaxes(1 + rate/100);
    }

    @Then("the price without taxes is {double} dollars")
    public void the_price_without_taxes_is_$(Double expected) {
        assertEquals(expected, o.computePrice(catalogue), 0.01);
    }

    @Then("the price including taxes is {double} dollars")
    public void the_price_including_taxes_is_$(Double expected) {
        assertEquals(expected, o.computePriceWithTaxes(catalogue), 0.01);
    }

    
}
