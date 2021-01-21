import React from 'react'
import service from "./service";
import Select from "react-select";
import BarChart from "./BarChart";


class InputForm extends React.Component {
    constructor(props) {
        super(props);
        this.state = {
            value: '',
            selectOptions: [],
            name: '',
            selectedOption: "",
            dateOption: [],
            selectedDate: ""
        };

        this.handleChange = this.handleChange.bind(this);
        this.handleSubmit = this.handleSubmit.bind(this);

    }


    async componentDidMount(prevProps, prevState) {
        console.log("INPUT WILL MOUNT GET ALL DATAS");
        await fetch(`http://localhost:8080/info/find-all`);
        this.getOptions();
    }

    async getOptions() {
        var data;
        var options;
        var dates;
        var dateOpt;

        service.getCityNames().then(response => {
            // console.log(response)
            data = response.data;
            options = data.map(d => ({
                "label": d
            }))
            this.setState({selectOptions: options})
        });

        service.getDates().then(response => {
            dates = response.data;
            dateOpt = dates.map(d => ({
                "label": d
            }))
            this.setState({dateOption: dateOpt})
        })

    }

    handleChange(event) {
        this.setState({value: event.target.value});
    }

    handleCityChange = selectedOption => {
        this.setState({selectedOption},);
        console.log(`Option selected:`, selectedOption.label);
    };

    handleDateChange = selectedDate => {
        this.setState({selectedDate},);
        console.log(`Date selected:`, selectedDate.label);
    }

    async handleSubmit(event) {
        if (this.state.value == '') {
            alert('BOŞ VERİ GİREMEZSİNİZ ' + this.state.value);
            event.preventDefault();
            return false;
        }
        alert('An input was submitted: ' + this.state.value);

        event.preventDefault();

        const news = {"text": this.state.value};
        var data = new FormData();
        data.append("json", JSON.stringify(news));

        await fetch('http://localhost:8080/info/post-news', {
            mode: 'cors',
            method: 'POST',
            headers: {
                'Accept': 'application/json',
                'Content-Type': 'application/json',
            },
            body: data,
        });

        // await client.createInfo(news);
        console.log((await service.createInfo(news)).status)
        console.log(this.state.value);

        this.setState({value: ""});

        this.componentDidMount();
    }

    render() {
        const {selectedOption} = this.state;
        const {selectedDate} = this.state;
        return (
            <form onSubmit={this.handleSubmit}>
                <label>
                    Haber :
                    <textarea
                        type="text"
                        value={this.state.value}
                        onChange={this.handleChange}
                        className="form-control"
                        id="exampleFormControlTextarea1"
                        rows={6}
                    />
                </label>
                <input type="submit" value="Gönder"/>

                <div>
                    <Select options={this.state.selectOptions}
                            value={selectedOption}
                            onChange={this.handleCityChange}
                            placeholder={"Şehir seçin.."}
                    />
                </div>
                <div>
                    <Select options={this.state.dateOption}
                            value={selectedDate}
                            onChange={this.handleDateChange}
                            placeholder={"Tarih seçin.."}
                    />
                </div>
                <BarChart date={selectedDate.label} location={selectedOption.label} legendPosition="bottom"/>
            </form>

        );
    }
}

export default InputForm;