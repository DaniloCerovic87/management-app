insert into department values(1, 'IT', null);
insert into department values(2, 'HR', null);
insert into employee values(1, 'Nikola', '425266', 1);
insert into employee values(2, 'Milica', '643635', 1);
insert into employee values(3, 'Dejan','737377', 1);
insert into employee values(4, 'Sofija', '423423', 1);
insert into employee values(5, 'Irena', '543543', 1);
insert into employee values(6, 'Natalija', '535433', 2);
update department set employee_id = 2 WHERE department_id = 1;
