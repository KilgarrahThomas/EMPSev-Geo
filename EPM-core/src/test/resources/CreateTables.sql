CREATE TABLE IF NOT EXISTS Employee (
                                        empId SERIAL,
                                        name VARCHAR(45) NOT NULL,
                                        address VARCHAR(45) NOT NULL,
                                        mail VARCHAR(45) NOT NULL,
                                        paymentClassificationType VARCHAR(45) NOT NULL,
                                        paymentMethodType VARCHAR(45) NOT NULL,
                                        paymentScheduleType VARCHAR(45) NOT NULL,

                                        primary key (empId)
);

CREATE TABLE IF NOT EXISTS SalariedClassification (
                                      id SERIAL,
                                      mount NUMERIC(5,2),
                                      empId int,

                                      primary key (id),
                                      foreign key(empId) references Employee(empId)
);