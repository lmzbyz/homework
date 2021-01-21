import React from 'react';
import {Bar, Pie} from 'react-chartjs-2';
import service from "./service";
import {ResponsiveContainer} from "recharts";

class BarChart extends React.Component {
    constructor(props) {
        super(props);
        this.state = {
            chartData: {},
            chartDataDate :{},
            chartDataAll:{}
        }
    }


    async componentDidUpdate(prevProps, prevState) {
        var nums = [];

        if (prevProps.date !== this.props.date || prevProps.location !== this.props.location) {
            console.log("Eşit DEĞİl");

            await fetch('http://localhost:8080/info/data-of-city-date', {
                mode: 'cors',
                method: 'POST',
                headers: {
                    'Accept': 'application/json',
                    'Content-Type': 'application/json',
                },
                body: this.props.location + "," + this.props.date,
            });

            service.getCountDates().then(response => {
                nums = response.data;
                // console.log(response.data)
                this.getChartDataDate(nums);
            })
        // }
        // if (prevProps.location !== this.props.location) {
            await fetch('http://localhost:8080/info/data-of-city', {
                mode: 'cors',
                method: 'POST',
                headers: {
                    'Accept': 'application/json',
                    'Content-Type': 'application/json',
                },
                body: this.props.location,
            });

            service.getCounts().then(response => {
                nums = response.data;
                // console.log(response.data)
                this.getChartData(nums);
            })
        }
    }

    // componentDidMount() {
    //     console.log("hello")
    //     var numsAll = [];
    //     client.getAllCounts().then(response => {
    //         numsAll = response.data;
    //
    //         this.getChartDataAll(numsAll);
    //     })
    // }

    // getChartDataAll(numsAll){
    //     this.setState({
    //         chartDataAll: {
    //             labels: ['Vefat', 'Taburcu', 'Vaka'],
    //             datasets: [
    //                 {
    //                     label: '# of People',
    //                     data: numsAll,
    //                     backgroundColor: [
    //                         'rgba(255, 99, 132, 0.2)',
    //                         'rgba(54, 162, 235, 0.2)',
    //                         'rgba(255, 206, 86, 0.2)'
    //                     ]
    //                 }
    //             ]
    //         }
    //     });
    // }

    getChartDataDate(nums) {
        this.setState({
            chartDataDate: {
                labels: ['Vefat', 'Taburcu', 'Vaka'],
                datasets: [
                    {
                        label: '# of People',
                        data: nums,
                        backgroundColor: [
                            'rgba(255, 99, 132, 0.2)',
                            'rgba(54, 162, 235, 0.2)',
                            'rgba(255, 206, 86, 0.2)'
                        ]
                    }
                ]
            }
        });
    }


    getChartData(nums) {
        this.setState({
            chartData: {
                labels: ['Vefat', 'Taburcu', 'Vaka'],
                datasets: [
                    {
                        label: '# of People',
                        data: nums,
                        backgroundColor: [
                            'rgba(255, 99, 132, 0.2)',
                            'rgba(54, 162, 235, 0.2)',
                            'rgba(255, 206, 86, 0.2)'
                        ]
                    }
                ]
            }
        });
    }

    static defaultProps = {
        displayTitle: true,
        displayLegend: true,
        legendPosition: 'right',
        location: ''
    }

    render() {
        return (
            <form>
                <div className="chart2">
                    <ResponsiveContainer width="80%" height={300}>
                        <Bar
                            data={this.state.chartData}
                            options={{
                                scales: {
                                    dataset: [{
                                        barThickness: 200
                                    }]
                                },
                                title: {
                                    display: this.props.displayTitle,
                                    text: 'Tüm Veriler ' + this.props.location,
                                    fontSize: 25
                                },
                                legend: {
                                    display: this.props.displayLegend,
                                    position: this.props.legendPosition
                                }
                            }}
                        />
                    </ResponsiveContainer>
                    <ResponsiveContainer width="80%" height={300}>
                        <Bar
                            data={this.state.chartDataDate}
                            options={{
                                scales: {
                                    dataset: [{
                                        barThickness: 200
                                    }]
                                },
                                title: {
                                    display: this.props.displayTitle,
                                    text: 'Günlük Veriler ' + this.props.location + ' '+ this.props.date,
                                    fontSize: 25
                                },
                                legend: {
                                    display: this.props.displayLegend,
                                    position: this.props.legendPosition
                                }
                            }}
                        />
                    </ResponsiveContainer>
                    {/*<ResponsiveContainer width="95%" height={400}>*/}
                    {/*    <Line*/}
                    {/*        data={this.state.chartData}*/}
                    {/*        options={{*/}
                    {/*            title: {*/}
                    {/*                display: this.props.displayTitle,*/}
                    {/*                text: 'Veriler ' + this.props.location,*/}
                    {/*                fontSize: 25*/}
                    {/*            },*/}
                    {/*            legend: {*/}
                    {/*                display: this.props.displayLegend,*/}
                    {/*                position: this.props.legendPosition*/}
                    {/*            }*/}
                    {/*        }}*/}
                    {/*    />*/}
                    {/*</ResponsiveContainer>*/}
                </div>
            </form>
        )
    }
}

export default BarChart;