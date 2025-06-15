import { EndpointRequestInit as EndpointRequestInit_1 } from "@vaadin/hilla-frontend";
import type Album_1 from "./com/unl/practica2/base/models/Album.js";
import client_1 from "./connect-client.default.js";
async function createAlbum_1(nombre: string | undefined, fecha: string | undefined, init?: EndpointRequestInit_1): Promise<void> { return client_1.call("AlbumService", "createAlbum", { nombre, fecha }, init); }
async function listAllAlbum_1(init?: EndpointRequestInit_1): Promise<Array<Album_1 | undefined> | undefined> { return client_1.call("AlbumService", "listAllAlbum", {}, init); }
async function updateAlbum_1(id: number | undefined, nombre: string | undefined, fecha: string | undefined, init?: EndpointRequestInit_1): Promise<void> { return client_1.call("AlbumService", "updateAlbum", { id, nombre, fecha }, init); }
export { createAlbum_1 as createAlbum, listAllAlbum_1 as listAllAlbum, updateAlbum_1 as updateAlbum };
