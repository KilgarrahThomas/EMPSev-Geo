import React from 'react';
import axios from 'axios';

export default class EmployeeList extends React.Component {
    state = {
        employees: [],
    };

    componentDidMount() {
        axios.get ('http://localhost:8080/api/employee')
        .then(res => {
            console.log(res);
            this.setState({ employees : res.data});
        });
    }

    render() {
    return (
    
    <table className="table">
  <thead>
    <tr>
      <th scope="col">#</th>
      <th scope="col">Name</th>
      <th scope="col">Address</th>
    </tr>
  </thead>
  <tbody>
    
    {this.state.employees.map(employee => <tr key={employee.empID}><th scope="row">{employee.empID}</th>
      <td>{employee.name}</td>
      <td>{employee.address}</td>
      </tr>
    )}
    
    
    
  </tbody>
</table>
    
   
     ) }
}