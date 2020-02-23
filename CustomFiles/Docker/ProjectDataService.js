import axios from 'axios'

const API_URL = 'http://localhost:8080';
class ProjectDataService {

	getProcessStatus()
	{
		return axios.get(`${API_URL}/getProcessStatus`);
	}

	getBoardData(count)
	{
		return axios.get(`${API_URL}/getBoardData/${count}`);
	}

	sendSchematic(data)
	{
		return axios.post(`${API_URL}/uploadSchematicData`, data);
	}
	sendSchematicId(id)
	{
		return axios.get(`${API_URL}/sendSchematicId/${id}`);
	}
}

export default new ProjectDataService()
