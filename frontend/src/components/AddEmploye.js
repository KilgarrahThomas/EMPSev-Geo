import React from 'react';
import axios from 'axios';



class AddEmploye extends React.Component{
    state = {
        nom: "",
        address:"",
        type :"",
        method:"",
        schedule:""
    };

    handleSubmit  = event => {
        event.preventDefault();
        const user = {
            nom : this.state.nom,
            address: this.state.address,
            type : this.state.type,
            method: this.state.method,
            schedule:  this.state.schedule
        };
            axios.post("http://localhost:8080/addEmploye", null, {
                params : {
                    nom : user.nom,
                    addresse : user.address,
                    method : user.method,
                    schedule : user.schedule,
                    type  : user.type
                }
            }).then(reponse => {
                console.log(reponse);
            }).catch(err =>{
                console.log(err);
            })
    };




    handleChangeName  = event => {
        this.setState({nom : event.target.value})

    };
    handleChangeAddress = event => {
        this.setState({address : event.target.value})
    };

    handleChangeType = event =>  {
        this.setState({type : event.target.value})
    };
    handleChangeMethod = event => {
        this.setState({method: event.target.value})
    };
    handleChangeSchedule = event => {
        this.setState({schedule : event.target.value})
    };


    render(){
        return(
            <div>
                <h1 className="d-flex justify-content-center">Ajout d'un membre du personnel </h1>
                <form onSubmit={this.handleSubmit}>
                <div className="col">
                    <div className="col mb-2 w-25">
                        <input type="text" className="form-control" placeholder="Nom" onChange={this.handleChangeName}/>
                    </div>
                    <div className="col mb-2 w-25">
                        <input type="text" className="form-control" placeholder="Addresse" onChange={this.handleChangeAddress}/>
                    </div>
                </div>

                <div className="mb-2">
                    <select className="browser-default custom-select w-25 ml-4" onChange={this.handleChangeType}>
                        <option disabled selected>Type de membre</option>
                        <option value="salarie">Salarié</option>
                        <option value="commission">Commission</option>
                        <option value="heure">Hourly</option>
                    </select>
                </div>
                    <div className="mb-2">
                    <select className="browser-default custom-select w-25 ml-4" onChange={this.handleChangeMethod}>
                        <option disabled selected >Moyen de payement</option>
                        <option value="direct">Direct</option>
                        <option value="mail">Mail</option>
                        <option value="cash">Cash</option>
                    </select>
                    </div>
                    <select className="browser-default custom-select w-25 ml-4" onChange={this.handleChangeSchedule}>
                        <option disabled selected >Dâte de paiement</option>
                        <option value="semaine">la semaine</option>
                        <option value="2semaines">toutes les 2 semaines</option>
                        <option value="mois">mois</option>
                    </select>

                <div className="ml-4 mt-2">
                    <button className="btn btn-primary  w-25" type="submit"> Valider </button>
                </div>
                </form>
            </div>

        )
    }
}

export default AddEmploye;