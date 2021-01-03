package be.heh.epm.database;

import be.heh.epm.application.logger.Logger;
import be.heh.epm.application.port.out.EmployeeGateway;
import be.heh.epm.domain.classification.*;
import be.heh.epm.domain.employee.Employee;
import be.heh.epm.domain.payDay.PayCheck;
import be.heh.epm.domain.payMethod.DirectDepositMethod;
import be.heh.epm.domain.payMethod.MailMethod;
import be.heh.epm.domain.schedule.MonthlyPaymentSchedule;
import be.heh.epm.domain.schedule.TwoWeeksPayementSchedule;
import be.heh.epm.domain.schedule.WeeklyPaymentSchedule;

import java.sql.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class DataBaseHelper implements EmployeeGateway {

    //Preparation de la DB
    private String url = "jdbc:postgresql://127.0.0.1:5432/epm";
    private String user = "postgres";
    private String password = "root";
    private Connection connection = null;
    private Statement stmt;

    //Connection
    public DataBaseHelper() {
        Connect();
    }
    private Connection Connect() {
        try {
            this.connection = DriverManager.getConnection(this.url, this.user, this.password);
            System.out.println("Connected to database successfully");
            Logger.LogInfo(this.getClass().getName(), "Connected to Database");
//            stmt = this.connection.createStatement();
//            String sql = "CREATE TABLE employees " +
//                    "(idemp SERIAL PRIMARY KEY ," +
//                    " name           TEXT    NOT NULL UNIQUE, " +
//                    " address        CHAR(50))"; //Crée la table Employees avec les collones et parametres spécifié.
//            stmt.executeUpdate(sql);
//            stmt.close();
        } catch (SQLException e) {
            System.out.println("Cannot connect to database\n" + e.getMessage());
            Logger.LogError(this.getClass().getName(), "Cannot connect to database\n" + e.getMessage());
        }
        return this.connection;
    }

    //Retourne tout les employee de la DB
    public Map getAllEmployees() {
        Map<Integer, Employee> employeeMap = new TreeMap<>();
        Logger.LogInfo(this.getClass().getName(), "getAll preparation");
        String sql = "SELECT * FROM employees";
        try (PreparedStatement ps = this.connection.prepareStatement(sql)) {
            Logger.LogInfo(this.getClass().getName(), "Récupération en cours");
            ResultSet rs = ps.executeQuery();
            if(!rs.next()) {
                Logger.LogInfo(this.getClass().getName(), "Rien n'a été récupéré");
                return null;
            }
            else do{
                Logger.LogInfo(this.getClass().getName(), "Récupération de l'employé");
                Employee emp = new Employee();
                emp.setEmpId(rs.getInt("idemp"));
                emp.setName(rs.getString("name"));
                emp.setAddress(rs.getString("address"));
                emp.setMail(rs.getString("mail"));
                String classification = rs.getString("classification");

                switch (classification) {
                    case "salaried":
                        emp.setPayClassification(new SalariedClassification(rs.getDouble("salary")));
                        emp.setPaySchedule(new MonthlyPaymentSchedule());
                        break;
                    case "commissioned":
                        emp.setPayClassification(new CommissionClassification(rs.getDouble("salary"), rs.getDouble("commission")));
                        CommissionClassification cc = (CommissionClassification) emp.getPayClassification();
                        cc.setListSaleReceipt(getEmployeeSaleReceipt(emp.getEmpID()));
                        emp.setPaySchedule(new TwoWeeksPayementSchedule());
                        break;
                    case "hourlied" :
                        emp.setPayClassification(new HourlyClassification(rs.getDouble("salary")));
                        HourlyClassification hc = (HourlyClassification) emp.getPayClassification();
                        hc.setListTimeCards(getEmployeeTimeCard(emp.getEmpID()));
                        emp.setPaySchedule(new WeeklyPaymentSchedule());
                        break;
                }

                String method = rs.getString("paymethod");

                switch (method) {
                    case "mail" :
                        emp.setPayMethod(new MailMethod(emp.getMail()));
                        break;
                    case "directdeposit" :
                        emp.setPayMethod(new DirectDepositMethod(rs.getString("bank"), rs.getString("account")));
                        break;
                }

                employeeMap.put(rs.getInt("idemp"), emp);
                Logger.LogInfo(this.getClass().getName(), "Employee " + emp.getName() + " recovered");
            }while(rs.next());
        } catch (Exception e) {
            Logger.LogError(this.getClass().getName(), "Employee not recovered\n" + e.getMessage());
            System.out.println(e.getMessage());
        }
        return employeeMap;
    }

    //Retourne un employee spécifique de la DB
    public Employee getEmployee(int id) {
        String sql = "SELECT * FROM employees WHERE idemp=?";
        Employee emp = new Employee();
        try (PreparedStatement ps = this.connection.prepareStatement(sql)) {
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            while(rs.next()){
                emp.setEmpId(rs.getInt("idemp"));
                emp.setName(rs.getString("name"));
                emp.setAddress(rs.getString("address"));
                emp.setMail(rs.getString("mail"));
                String classification = rs.getString("classification");
                switch (classification) {
                    case "salaried":
                        emp.setPayClassification(new SalariedClassification(rs.getDouble("salary")));
                        emp.setPaySchedule(new MonthlyPaymentSchedule());
                        break;
                    case "commissioned":
                        emp.setPayClassification(new CommissionClassification(rs.getDouble("salary"), rs.getDouble("commission")));
                        CommissionClassification cc = (CommissionClassification) emp.getPayClassification();
                        cc.setListSaleReceipt(getEmployeeSaleReceipt(emp.getEmpID()));
                        emp.setPaySchedule(new TwoWeeksPayementSchedule());
                        break;
                    case "hourlied" :
                        emp.setPayClassification(new HourlyClassification(rs.getDouble("salary")));
                        HourlyClassification hc = (HourlyClassification) emp.getPayClassification();
                        hc.setListTimeCards(getEmployeeTimeCard(emp.getEmpID()));
                        emp.setPaySchedule(new WeeklyPaymentSchedule());
                        break;
                }

                String method = rs.getString("paymethod");

                switch (method) {
                    case "mail" :
                        emp.setPayMethod(new MailMethod(emp.getMail()));
                        break;
                    case "directdeposit" :
                        emp.setPayMethod(new DirectDepositMethod(rs.getString("bank"), rs.getString("account")));
                        break;
                }
            };
            if (emp.getName() == null){
                System.out.println("User not found");
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return emp;
    }

    //Insertion d'employee dans la DB
    public void createEmployee(Employee employee) {
        String sql = "INSERT INTO employees(name, address, mail, classification, salary, commission, paymethod, bank, account) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try (PreparedStatement ps = this.connection.prepareStatement(sql, new String[] { "idemp" })) {
            ps.setString(1, employee.getName());
            ps.setString(2, employee.getAddress());
            ps.setString(3, employee.getMail());
            ps.setString(4, employee.getPayClassification().toString());
            ps.setDouble(5, employee.getPayClassification().getSalary());
            ps.setDouble(6, employee.getPayClassification().getCommission());
            ps.setString(7, employee.getPayMethod().toDB());
            ps.setString(8, employee.getPayMethod().getBank());
            ps.setString(9, employee.getPayMethod().getAccount());
            int id = ps.executeUpdate();
            Logger.LogInfo(this.getClass().getName(), employee.getName() + " recorded");
            if (id > 0) {
                try (ResultSet rs = ps.getGeneratedKeys()) {
                    if (rs.next()) {
                        employee.setEmpId(rs.getInt("idemp"));
                        Logger.LogInfo(this.getClass().getName(), "ID reception : "+ employee.getEmpID());
                    }
                } catch (SQLException e) {
                    System.out.println(e.getMessage());
                    Logger.LogWarn(this.getClass().getName(), "No ID for" + employee.getName());
                }
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            Logger.LogError(this.getClass().getName(), employee.getName() + " not recorded");
        }
    }

    //Modification d'employee dans la DB
    public void save(int empId, Employee employee) {
        String sql = "UPDATE employees SET name = ?, address = ?, mail = ?, classification = ?, " +
                "salary = ?, commission = ?, paymethod = ?, bank = ?, account = ? WHERE idemp = ?";
        try (PreparedStatement ps = this.connection.prepareStatement(sql)) {
            ps.setString(1, employee.getName());
            ps.setString(2, employee.getAddress());
            ps.setString(3, employee.getMail());
            ps.setString(4, employee.getPayClassification().toString());
            ps.setDouble(5, employee.getPayClassification().getSalary());
            ps.setDouble(6, employee.getPayClassification().getCommission());
            ps.setString(7, employee.getPayMethod().toDB());
            ps.setString(8, employee.getPayMethod().getBank());
            ps.setString(9, employee.getPayMethod().getAccount());
            ps.setInt(10, employee.getEmpID());
            int id = ps.executeUpdate();
            Logger.LogInfo(this.getClass().getName(), "Employee " + employee.getEmpID() + " modified");
            if (id > 0) {
                try (ResultSet rs = ps.getGeneratedKeys()) {
                    if (rs.next()) {
                        employee.setEmpId(rs.getInt("idemp"));
                        Logger.LogInfo(this.getClass().getName(), "ID reception : "+ employee.getEmpID());
                    }
                } catch (SQLException e) {
                    System.out.println(e.getMessage());
                    Logger.LogWarn(this.getClass().getName(), "No ID for" + employee.getName());
                }
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            Logger.LogError(this.getClass().getName(), "Employee " + employee.getEmpID() + " not modified");
        }
    }

    //Deletion d'employee dans la DB
    public void deleteEmployee(int id) {
        String sql = "DELETE FROM employees WHERE idemp=?";
        Logger.LogInfo(this.getClass().getName(), "delete preparation");
        try (PreparedStatement ps = this.connection.prepareStatement(sql)) {
            ps.setInt(1, id);
            ps.executeUpdate();
            Logger.LogInfo(this.getClass().getName(), "Employee " + id + " deleted");
            System.out.println("Employee " + getEmployee(id).getName() + " deleted");
        } catch (Exception e) {
            Logger.LogError(this.getClass().getName(), "Employee " + id + " cannot be deleted\n" + e.getMessage());
            System.out.println(e.getMessage());
        }
    }

    public void addEmployeeTimeCard (int id, TimeCard tc) {
        String sql = "INSERT INTO TimeCard (idemp, date, hours) VALUES (?, ?, ?)";
        try(PreparedStatement ps = this.connection.prepareStatement(sql, new String[] { "idtc" })) {
            ps.setInt(1, id);
            ps.setDate(2, DateConvert.LocalDateToDate(tc.getDate()));
            ps.setDouble(3, tc.getHours());
            int tcID = ps.executeUpdate();
            Logger.LogInfo(this.getClass().getName(), "New TimeCard for Employee " + id +" recorded");
        } catch (SQLException e) {
            Logger.LogError(this.getClass().getName(), "TimeCard not recorded\n" + e.getMessage());
        }
    }

    public HashMap getEmployeeTimeCard(int id) {
        HashMap<LocalDate, TimeCard> listTimeCards = new HashMap<LocalDate, TimeCard>();
        Logger.LogInfo(this.getClass().getName(), "Recovering Timecards preparation");
        String sql ="SELECT * FROM TimeCard WHERE idemp = ?";
        try (PreparedStatement ps = this.connection.prepareStatement(sql)) {
            ps.setInt(1, id);
            Logger.LogInfo(this.getClass().getName(), "Recovering");

            ResultSet rs = ps.executeQuery();
            if(!rs.next()) {
                Logger.LogInfo(this.getClass().getName(), "No TimeCards");
            }
            else do {
                Logger.LogInfo(this.getClass().getName(), "TimeCard recovering");
                TimeCard tc = new TimeCard(DateConvert.DateToLocalDate(rs.getDate("date")), rs.getDouble("hours"));
                listTimeCards.put(tc.getDate(), tc);
                Logger.LogInfo(this.getClass().getName(), "TimeCard Recovered");
            }while(rs.next());
        } catch (SQLException e) {
            Logger.LogError(this.getClass().getName(), "TimeCard not recovered");
        }
        return listTimeCards;
    }

    @Override
    public void addEmployeeSaleReceipt(int id, SaleReceipt sr) {
        String sql = "INSERT INTO salesreceipt (idemp, date, amount) VALUES (?, ?, ?)";
        try(PreparedStatement ps = this.connection.prepareStatement(sql, new String[] { "idsr" })) {
            ps.setInt(1, id);
            ps.setDate(2, DateConvert.LocalDateToDate(sr.getDate()));
            ps.setDouble(3, sr.getSaleAmount());
            Logger.LogInfo(this.getClass().getName(), "New SaleReceipt for Employee " + id +" recorded");
        } catch (SQLException e) {
            Logger.LogError(this.getClass().getName(), "SaleReceipt not recorded\n" + e.getMessage());
        }
    }

    @Override
    public HashMap getEmployeeSaleReceipt(int id) {
        HashMap<LocalDate, SaleReceipt> listSaleReceipt = new HashMap<LocalDate, SaleReceipt>();
        Logger.LogInfo(this.getClass().getName(), "Recovering SaleReceipt preparation");
        String sql ="SELECT * FROM salesreceipt WHERE idemp = ?";
        try (PreparedStatement ps = this.connection.prepareStatement(sql)) {
            ps.setInt(1, id);
            Logger.LogInfo(this.getClass().getName(), "Recovering");

            ResultSet rs = ps.executeQuery();
            if(!rs.next()) {
                Logger.LogInfo(this.getClass().getName(), "No SaleReceipt");
            }
            else do {
                Logger.LogInfo(this.getClass().getName(), "SaleReceipt recovering");
                SaleReceipt sr = new SaleReceipt(DateConvert.DateToLocalDate(rs.getDate("date")), rs.getDouble("amount"));
                listSaleReceipt.put(sr.getDate(), sr);
                Logger.LogInfo(this.getClass().getName(), "SaleReceipt Recovered");
            }while(rs.next());
        } catch (SQLException e) {
            Logger.LogError(this.getClass().getName(), "SaleReceipt not recovered");
        }
        return listSaleReceipt;
    }

    @Override
    public void addPayCheck(int idemp, PayCheck pc) {
        String sql ="INSERT INTO payroll (idemp, date, salary, transfert) VALUES (?, ?, ?, ?)";
        try(PreparedStatement ps = this.connection.prepareStatement(sql, new String[] { "idpr" })) {
            ps.setInt(1, idemp);
            ps.setDate(2, DateConvert.LocalDateToDate(pc.getDate()));
            ps.setDouble(3, pc.getSalary());
            ps.setString(4, pc.getMethod());
            ps.executeUpdate();
            Logger.LogInfo(this.getClass().getName(), "New SaleReceipt for Employee " + idemp +" recorded");
        } catch(SQLException e) {
            Logger.LogError(this.getClass().getName(), "SaleReceipt not recorded for Employee " + idemp +"\n" + e.getMessage());
        }
    }

    @Override
    public HashMap getPayCheckForDate(LocalDate date) {
        HashMap<Employee, PayCheck> listPayCheck  = new HashMap<>();
        Logger.LogInfo(this.getClass().getName(), "Recovering PayCheck for " + date.toString() + " preparation");
        String sql = "SELECT * FROM payroll WHERE date = ?";
        try (PreparedStatement ps = this.connection.prepareStatement(sql)) {
            ps.setDate(1, DateConvert.LocalDateToDate(date));
            Logger.LogInfo(this.getClass().getName(), "Recovering");

            ResultSet rs = ps.executeQuery();
            if(!rs.next()) {
                Logger.LogInfo(this.getClass().getName(), "No Paycheck for " + date.toString());
            }
            else do {
                PayCheck pc = new PayCheck(date);
                Logger.LogInfo(this.getClass().getName(), "Paycheck recovering");
                pc.setPay(rs.getDouble("salary"));
                pc.setMethod(rs.getString("transfert"));
                listPayCheck.put(getEmployee(rs.getInt("idemp")), pc);
                Logger.LogInfo(this.getClass().getName(), "Paycheck Recovered");
            }while(rs.next());
        } catch (SQLException e) {
            Logger.LogError(this.getClass().getName(), "PayCheck not recovered");
        }
        return listPayCheck;
    }
}