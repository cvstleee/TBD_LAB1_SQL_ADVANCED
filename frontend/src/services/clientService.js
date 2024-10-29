import httpClient from './httpClient';

const clientService =(data) => {
    return httpClient.post('api/v1/clients', data);
}
