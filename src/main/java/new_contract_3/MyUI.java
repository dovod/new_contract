package new_contract_3;

import com.vaadin.annotations.Theme;
import com.vaadin.annotations.VaadinServletConfiguration;
import com.vaadin.data.Binder;
import com.vaadin.data.ValidationException;
import com.vaadin.data.converter.LocalDateTimeToDateConverter;
import com.vaadin.data.converter.StringToDoubleConverter;
import com.vaadin.data.validator.DoubleRangeValidator;
import com.vaadin.server.ThemeResource;
import com.vaadin.server.VaadinRequest;
import com.vaadin.server.VaadinServlet;
import com.vaadin.shared.ui.ContentMode;
import com.vaadin.ui.*;

import javax.servlet.annotation.WebServlet;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.ZoneId;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.Properties;

/**
 * This UI is the application entry point. A UI may either represent a browser window
 * (or tab) or some part of an HTML page where a Vaadin application is embedded.
 * <p>
 * The UI is initialized using {@link #init(VaadinRequest)}. This method is intended to be
 * overridden to add component to the user interface and initialize non-component functionality.
 */
@Theme("mytheme")
public class MyUI extends UI {

    @Override
    protected void init(VaadinRequest vaadinRequest) {
        Window newWindow = new Window();
        AbsoluteLayout absLyout = new AbsoluteLayout();
        Contract newcontract = new Contract();
        Label lblnewcontract = new Label("<font size=\"6\">Новый договор</font>");
        lblnewcontract.setContentMode(ContentMode.HTML);
        absLyout.addComponent(lblnewcontract, "left: 3%; top: 2%;");
        Label lblAllincusive = new Label("<font size=\"4\">Все включено</font>");
        lblAllincusive.setContentMode(ContentMode.HTML);
        absLyout.addComponent(lblAllincusive, "right: 3%; top: 4%;");
        DateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy");
        Date date_of_commencement = new Date();
        Calendar calendar0 = Calendar.getInstance();
        calendar0.add(Calendar.DATE, 10);
        date_of_commencement = calendar0.getTime();
        newcontract.setDateOfCommencement(date_of_commencement);
        Date expiry_date = new Date();
        Calendar calendar1 = Calendar.getInstance();
        calendar1.add(Calendar.YEAR, 1);
        calendar1.add(Calendar.DATE, 9);
        expiry_date = calendar1.getTime();
        newcontract.setExpiryDate(expiry_date);
        Date date_of_contract = new Date();
        newcontract.setDateOfContract(date_of_contract);
        String strdate_of_contract = "Дата заключения: " + dateFormat.format(date_of_contract);
        absLyout.addComponent(new Label(strdate_of_contract), "left: 3%; top: 15%;");
        String strDateNow = "Срок действия: с " + dateFormat.format(date_of_commencement) + " по " + dateFormat.format(expiry_date);
        Label lblDateNow = new Label(strDateNow);
        absLyout.addComponent(lblDateNow, "right: 3%; top: 15%;");
        Label lblInsurant = new Label("<font size=\"4\">Страхователь</font>");
        lblInsurant.setContentMode(ContentMode.HTML);
        absLyout.addComponent(lblInsurant, "left: 3%; top: 25%;");
        TextField tfFIO = new TextField("ФИО страхователя");
        tfFIO.setWidth(35.0f, Unit.PERCENTAGE);
        tfFIO.setRequiredIndicatorVisible(true);
        absLyout.addComponent(tfFIO, "left: 3%; top: 35%;");
        Button btnSexF = new Button("Женский");
        btnSexF.setWidth(15.0f, Unit.PERCENTAGE);
        btnSexF.addClickListener(e -> newcontract.setSex("female"));
        absLyout.addComponent(btnSexF, "left: 39%; top: 35%;");
        Button btnSexM = new Button("Мужской");
        btnSexM.setWidth(18.0f, Unit.PERCENTAGE);
        btnSexM.addClickListener(e -> newcontract.setSex("male"));
        absLyout.addComponent(btnSexM, "left: 50%; top: 35%;");
        DateTimeField dtfDate_of_birth = new DateTimeField("Дата рождения");
        dtfDate_of_birth.setRequiredIndicatorVisible(true);
        dtfDate_of_birth.setDateFormat("dd MM yyyy");
        dtfDate_of_birth.setWidth(38.0f, Unit.PERCENTAGE);
        absLyout.addComponent(dtfDate_of_birth, "left: 61%; top: 35%;");
        TextField tfPhone_number = new TextField("Телефон");
        tfPhone_number.setRequiredIndicatorVisible(true);
        tfPhone_number.setWidth(75.0f, Unit.PERCENTAGE);
        absLyout.addComponent(tfPhone_number, "left: 78%; top: 35%;");
        Label lblinsurance_object = new Label("<font size=\"4\">Объект страхования</font>");
        lblinsurance_object.setContentMode(ContentMode.HTML);
        absLyout.addComponent(lblinsurance_object, "left: 3%; top: 45%;");
        TextField tfPlace_of_insurance = new TextField("Место страхования");
        tfPlace_of_insurance.setRequiredIndicatorVisible(true);
        tfPlace_of_insurance.setWidth(95.0f, Unit.PERCENTAGE);
        absLyout.addComponent(tfPlace_of_insurance, "left: 3%; top: 55%;");
        Label lblinsurance_conditions = new Label("<font size=\"4\">Условия страхования</font>");
        lblinsurance_conditions.setContentMode(ContentMode.HTML);
        absLyout.addComponent(lblinsurance_conditions, "left: 3%; top: 65%;");
        TextField tfsum_insured = new TextField("Страховая сумма");
        tfsum_insured.setWidth(10.0f, Unit.PERCENTAGE);
        absLyout.addComponent(tfsum_insured, "left: 3%; top: 75%;");
        TextField tfinsurance_premium = new TextField("Страховая премия");
        tfinsurance_premium.setWidth(12.0f, Unit.PERCENTAGE);
        absLyout.addComponent(tfinsurance_premium, "left: 17%; top: 75%;");
        Button btnSave = new Button("Сохранить");
        absLyout.addComponent(btnSave, "left: 3%; top: 85%;");
        newWindow.setContent(absLyout);
        newWindow.setClosable(false);
        newWindow.setResizable(false);
        newWindow.setHeight(65.0f, Unit.PERCENTAGE);
        newWindow.setWidth(55.0f, Unit.PERCENTAGE);
        newWindow.center();
        addWindow(newWindow);
        Binder<Contract> binder = new Binder<>(Contract.class);
        binder.forField(tfFIO)
                .asRequired("Name may not be empty")
                .bind(Contract::getFio, Contract::setFio);
        binder.forField(dtfDate_of_birth)
                .asRequired("Date may not be empty")
                .withConverter(new LocalDateTimeToDateConverter(ZoneId.systemDefault()))
                .bind(Contract::getDateOfBirth, Contract::setDateOfBirth);
        binder.forField(tfPhone_number)
                .asRequired("Phone number may not be empty")
                .bind(Contract::getPhoneNumber, Contract::setPhoneNumber);
        binder.forField(tfPlace_of_insurance)
                .asRequired("Place of insurance may not be empty")
                .bind(Contract::getPlaceOfInsurance, Contract::setPlaceOfInsurance);
        binder.forField(tfsum_insured)
                .asRequired("Sum insured may not be empty")
                .withConverter(new StringToDoubleConverter("Could not convert value to Double"))
                .withValidator(new DoubleRangeValidator("Not a valid sum", 1.0, 1000000.0))
                .bind(Contract::getSumInsured, Contract::setSumInsured);
        binder.forField(tfinsurance_premium)
                .asRequired("insurance premium may not be empty")
                .withConverter(new StringToDoubleConverter("Could not convert value to Double"))
                .withValidator(new DoubleRangeValidator("Not a valid sum", 1.0, 1000000.0))
                .bind(Contract::getInsurancePremium, Contract::setInsurancePremium);

        binder.setBean(newcontract);
        btnSave.addClickListener(clickEvent -> {
            try {
                binder.writeBean(newcontract);
            } catch (ValidationException e) {
                e.printStackTrace();
            }
            absLyout.addComponent(new Label("Договор добавлен! "), "left: 3%; bottom: 5%;");
            newWindow.setClosable(true);
            sqlRequest(newcontract);
        });
        Image imgHead = new Image("", new ThemeResource("img/head_img1.png"));
        setContent(imgHead);
    }

    private void sqlRequest(Contract newcontract) {
        String userName = null;
        String password = null;
        String url_db = null;
        String testString = null;
        Properties props = new Properties();
        InputStream inputStream = MyUI.class.getClassLoader().getResourceAsStream("config.properties");
        try {
            props.load(inputStream);
            userName = props.getProperty("userName_db");
            password = props.getProperty("password_db");
            url_db = props.getProperty("url_db");
        } catch (IOException e) {
            System.out.println("config.properties not found");
            e.printStackTrace();
        }
        try {
            Connection conn = DriverManager.getConnection(url_db, userName, password);
            Statement statement = conn.createStatement();
            SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yy HH:mm:ss", Locale.ENGLISH);
            statement.executeUpdate("INSERT INTO contracts_tab3 VALUES(\n" +
                    "NULL ,\n" +
                    " TO_DATE('" + dateFormat.format(newcontract.getDateOfCommencement()) + "','DD/MM/YY HH24:MI:SS'),\n" +
                    " TO_DATE('" + dateFormat.format(newcontract.getExpiryDate()) + "','DD/MM/YY HH24:MI:SS'),\n" +
                    " TO_DATE('" + dateFormat.format(newcontract.getDateOfContract()) + "','DD/MM/YY HH24:MI:SS'),\n" +
                    " insurant_typ3(null,\n" +
                    "'" + newcontract.getFio() + "',\n" +
                    "'" + newcontract.getSex() + "',\n" +
                    " TO_DATE('" + dateFormat.format(newcontract.getDateOfBirth()) + "','DD/MM/YY HH24:MI:SS'),\n" +
                    newcontract.getPhoneNumber() + " ),\n" +
                    "insurance_conditions_typ3(" + newcontract.getSumInsured() + ",\n" +
                    newcontract.getInsurancePremium() + " ),\n" +
                    "insurance_object_typ3(null,\n" +
                    "'" + newcontract.getPlaceOfInsurance() + "'))");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @WebServlet(urlPatterns = "/*", name = "MyUIServlet", asyncSupported = true)
    @VaadinServletConfiguration(ui = MyUI.class, productionMode = false)
    public static class MyUIServlet extends VaadinServlet {
    }
}

class Contract {
    // свойства класса
    private int contracts_id; // ID контракта
    private Date date_of_commencement; // дата начала дествия контракта
    private Date expiry_date; // дата окончания действия контракта
    private Date date_of_contract; //Дата подписания контракта
    private int insurant_id; // ID страхователя
    private String fio; // ФИО страхователя
    private String sex; // пол страхователя
    private Date date_of_birth; // Дата рождения страхователя
    private String phone_number; // номер телефона страхователя
    private int insurance_object_id; // ID объекта страхования
    private String place_of_insurance; // Место страхования
    private double sum_insured; // Страховая сумма
    private double insurance_premium; // Страховая сумма

    public int getContractsID() {
        return contracts_id;
    }

    public void setContractsID(int contracts_id) {
        this.contracts_id = contracts_id;
    }

    public Date getDateOfCommencement() {
        return date_of_commencement;
    }

    public void setDateOfCommencement(Date date_of_commencement) {
        this.date_of_commencement = date_of_commencement;
    }

    public Date getExpiryDate() {
        return expiry_date;
    }

    public void setExpiryDate(Date expiry_date) {
        this.expiry_date = expiry_date;
    }

    public Date getDateOfContract() {
        return date_of_contract;
    }

    public void setDateOfContract(Date date_of_contract) {
        this.date_of_contract = date_of_contract;
    }

    public int getInsurantID() {
        return insurant_id;
    }

    public void setInsurantID(int insurant_id) {
        this.insurant_id = insurant_id;
    }

    public void setFio(String fio) {
        this.fio = fio;
    }

    public String getFio() {
        return fio;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getSex() {
        return sex;
    }

    public void setDateOfBirth(Date date_of_birth) {
        this.date_of_birth = date_of_birth;
    }

    public Date getDateOfBirth() {
        return date_of_birth;
    }

    public void setPhoneNumber(String phone_number) {
        this.phone_number = phone_number;
    }

    public String getPhoneNumber() {
        return phone_number;
    }

    public int getInsuranceObjectID() {
        return insurance_object_id;
    }

    public void setInsuranceObjectID(int insurance_object_id) {
        this.insurance_object_id = insurance_object_id;
    }

    public String getPlaceOfInsurance() {
        return place_of_insurance;
    }

    public void setPlaceOfInsurance(String place_of_insurance) {
        this.place_of_insurance = place_of_insurance;
    }

    public double getSumInsured() {
        return sum_insured;
    }

    public void setSumInsured(double sum_insured) {
        this.sum_insured = sum_insured;
    }

    public double getInsurancePremium() {
        return insurance_premium;
    }

    public void setInsurancePremium(double insurance_premium) {
        this.insurance_premium = insurance_premium;
    }
}

