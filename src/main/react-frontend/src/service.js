import axios from 'axios'

const rest_api_url_findAll = 'http://localhost:8080/info/find-all';
const rest_api_url_create = 'http://localhost:8080/info/post-news';
const rest_api_url_counts = 'http://localhost:8080/info/get-counts';
const rest_api_url_cities = 'http://localhost:8080/info/get-cities';
const rest_api_url_dates = 'http://localhost:8080/info/get-dates';
const rest_api_url_count_dates = 'http://localhost:8080/info/get-date-count';
const rest_api_url_all_counts = 'http://localhost:8080/info/get-all-counts';


class service {
    getInfos(){
        return axios.get(rest_api_url_findAll);
    }

    createInfo(news){
        return axios.post(rest_api_url_create, news);
    }

    getCounts(){
        return axios.get(rest_api_url_counts);
    }

    getAllCounts(){
        return axios.get(rest_api_url_all_counts);
    }

    getCityNames(){
        return axios.get(rest_api_url_cities);
    }

    getDates(){
        return axios.get(rest_api_url_dates);
    }

    getCountDates(){
        return axios.get(rest_api_url_count_dates);
    }
}

export default new service()